import React from "react";
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';
import './restaurant.css';

export default class Restaurant extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            items: [],
            restaurants: [],
            orderError: null,
            isOrdersLoaded: false,
            activeOrders: []
        };
    }

    componentDidMount() {
        this.renderPage()
    }

    renderPage() {
        fetch('http://localhost:8050/restaurant/api/v1/activeRestaurants')
            .then(response => response.json())
            .then(data => {
                this.setState({
                    restaurants: data
                });
                this.renderRestMenuAndOrders(data[0].id)
            });
    }

    render() {
        return (
            <>
                <div><h2>Restaurants</h2></div>
                <div>
                    {this.renderRestaurantNames()}
                </div>

                <div><h2>Menu</h2></div>
                {
                    this.renderMenuHTML()
                }

                <div><h2>Orders</h2></div>
                {
                    this.renderOrdersHTML()
                }
            </>
        );
    }

    /** display menu items of restaurant */
    renderMenuHTML() {
        const { error, isLoaded, items } = this.state;
        if (error) {
            return <div>Error: {error.message}</div>;
        } else if (!isLoaded) {
            return <div>Loading...</div>;
        } else {
            return (
                <div className="items" id="items">
                    {
                        items.map(item => (
                            <div className="items-box" key={item.id}>
                                <div className="item">
                                    <form onSubmit={(event) => this.saveMenuItem(event, item)}>
                                        <label>Name:
                                            <input name="ItemName" type="text" defaultValue={item.item_name} />
                                        </label>
                                        <label>Price(EUR):
                                            <input name="ItemPrice" type="number" step="0.1" defaultValue={item.price} />
                                        </label>
                                        <label>Status:
                                            <Dropdown options={this.showOrderStatusValues()} name="ItemStatus"
                                                onChange={(event) => this.updateItemStatus(event, item)}
                                                value={item.status} placeholder={item.status} />
                                        </label>
                                        <input type="submit" value="Save" />
                                    </form>
                                </div>
                            </div>
                        ))
                    }
                </div>
            );
        }
    }

    /** display names of restaurant which are available */
    renderRestaurantNames() {
        return (
            <div className="button">
                {
                    this.state.restaurants &&
                    this.state.restaurants.map((item) => {
                        return (
                            <button className="btn" value={item.name} onClick={() => {
                                this.renderRestMenuAndOrders(item.id)
                            }}>{item.name}</button>
                        )
                    })
                }
            </div>
        );
    }

    renderRestMenuAndOrders(restaurantid) {
        fetch('http://localhost:8050/restaurant/api/v1/getMenu/' + restaurantid)
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                        items: result
                    });
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error
                    });
                }
            )

        fetch('http://localhost:8050/restaurant/api/v1/getOrders/' + restaurantid)
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isOrdersLoaded: true,
                        activeOrders: result
                    });
                },
                (error) => {
                    this.setState({
                        isOrdersLoaded: true,
                        error
                    });
                }
            )
    }

    /** display restaurant orders along with status */
    renderOrdersHTML() {
        const { orderError, isOrdersLoaded, activeOrders } = this.state;
        if (orderError) {
            return <div>Error: {orderError.message}</div>;
        } else if (!isOrdersLoaded) {
            return <div>Loading...</div>;
        } else {
            return (
                <div className="items">
                    {
                        activeOrders.map(item => (
                            <div className="items-box" key={item.id}>
                                <div className="item">
                                    <h3>{item.id}</h3>
                                    <p>EUR {item.order_price}</p>
                                    {
                                        <Dropdown options={this.showDropDownValues(item)} disabled={this.disableDropDown(item)} onChange={this.updateOrderStatus} value={item.order_status} placeholder={item.order_status} order={item} />
                                    }
                                </div>
                            </div>
                        ))
                    }
                </div>
            );
        }
    }

    showDropDownValues(item) {
        const orderStatus = ['ORDERED', 'READY_FOR_PICKUP'];
        return orderStatus;
    }

    /** update status of the customer order */
    updateOrderStatus(item) {
        if (item.value === 'ACCEPT_ORDER') item.value = 'ORDER_ACCEPTED';
        this.order.order_status = item.value;
        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(this.order)
        };
        const endpoint = 'http://localhost:8050/restaurant/api/v1/updateOrderStatus';
        fetch(endpoint, requestOptions)
            .then(this.renderPage)
    }

    disableDropDown(item) {
        const orderStatus = ['ORDERED', 'ORDER_ACCEPTED', 'READY_FOR_PICKUP', 'DELIVERED'];
        if (item.order_status === orderStatus[0] || item.order_status === orderStatus[1]) {
            return false;
        }
        return true;
    }

    saveMenuItem = (event, item) => {

        const target = event.target;

        const item_name = target[0].value;
        const item_price = target[1].value;

        item.item_name = item_name;
        item.price = item_price;

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(item)
        };
        const endpoint = 'http://localhost:8050/restaurant/api/v1/addItemsToMenu/' + item.menu_id;
        fetch(endpoint, requestOptions)
            .then(this.renderPage())
    }

    showOrderStatusValues() {
        const val = ['ACTIVE', 'INACTIVE'];
        return val;
    }

    updateItemStatus(event, item) {
        item.status = event.value;
    }
}
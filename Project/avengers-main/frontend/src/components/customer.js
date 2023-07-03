import React from "react";
import { useState } from "react";
import Header from './header.js'
import Products from './products';
import Stomp from 'stompjs';
import '../App.css'

export default class Customer extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            items: [],
            restaurants: [],
            orderError: null,
            isOrdersLoaded: false,
            activeOrders: [],
            name: '',
            address: '',
            phone: '',
            isSubmit:false,
            customerData: {},
            items:[],
            restaurant_items:[],
            pastOrders: [],
            cart:[],
            restaurantID: '',
            customerOrder:{},
            customerOrderItems:[],
            setCustomerOrderItems:[],
            customerID:'',
            customerAddress:''
        };
    }

    

    componentDidMount() {
        // this.renderPage()
        fetch('http://localhost:8050/restaurant/api/v1/activeRestaurants')
            .then(response => response.json())
            .then(data => {
                console.log(data)
                    this.state.restaurants = data;
                    this.setState({
                        restaurants: data
                    });
            }
            );
        fetch('http://localhost:8050/restaurant/api/v1/restaurants/menus/items')
            .then(response => response.json())
            .then(data => {
                console.log(data)
                    this.state.items = data;
                    this.setState({
                        items: data
                    });
            }
            );
    }

    render() {
        return (
            <>
                <div> Hello Customer <h3>{this.state.name}</h3></div>
                
                <div>
                    {this.renderPage()}
                </div>
                <div><h2>Restaurants</h2></div>
                <div>
                    {this.renderRestaurantNames()}
                </div>
                <div>
                    {this.renderRestaurantMenu()}
                </div>
                <div>
                <button className="btn"
                                    onClick={() => {
                                        this.placeOrder();
                                    }}>Place Order</button>
                    {this.displayCart()}
                </div>
                <div>
                <button className="btn"
                                    onClick={() => {
                                        this.loadPastOrders();
                                    }}>Past Orders</button>
                {this.renderPastOrders()}
                </div>

            </>
        );
    }


    connectRabbit() {
        let stompClient;
        var ws = new WebSocket('ws://localhost:15674/ws');

        const headers = {
            'login': 'guest',
            'passcode': 'guest',
            'durable': true,
            'auto-delete': false
        };
        stompClient = Stomp.over(ws);

        stompClient.connect(headers, function (frame) {
            console.log('Connected')
            stompClient.subscribe('/queue/customer_delivery', function (message) {
                console.log(JSON.parse(message.body))
                let orderId = JSON.parse(message.body);
                alert(`${orderId.id} delivered`);    
            });
        });
    };


    renderPage() {
        return (
        <div>
            <form onSubmit={(event) => this.fetchCustomer(event)}>
                <label htmlFor="fname"> Name: </label>
                <input type="text" id="fname" name="fname"/>
                <label htmlFor="address"> Address: </label>
                <input type="text" id="address" name="address" />
                <label htmlFor="phone"> Phone: </label>
                <input type="text" id="phone" name="phone"/>
                <button type="submit">Login</button>
            </form>
            <br/>
            <br/>
            <div>
          </div>
        </div>
        )
    }

    fetchCustomer = (event) =>  {
        event.preventDefault();

        this.setState({
            name: event.target.elements.fname.value
        });
        this.setState({
            address: event.target.elements.address.value
        });
        this.setState({
            phone: event.target.elements.phone.value
        });

        this.state.name= event.target.elements.fname.value;
        this.state.address= event.target.elements.address.value;
        this.state.phone= event.target.elements.phone.value;
    if(null != this.state.name || null != this.state.address || null !=this.state.phone || '' != this.state.name || '' != this.state.address || '' !=this.state.phone) {
        this.setState({isSubmit: true}, () => {
            console.log(this.state.isSubmit);});
            console.log(this.state.isSubmit);
    }
        if (this.state.isSubmit) {

            let requestOptions = {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ 'name': this.state.name, 'address': this.state.address, 'phone': this.state.phone })
            };
            fetch('http://localhost:8050/customer/api/v1/customer', requestOptions)
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    this.setState({ customerData: data }, () => {
                        console.log(this.state.customerData);})
                    this.setState({ customerID: data.id }, () => {
                        console.log(this.state.customerID);})
                    this.setState({ customerAddress: data.address })
                    this.setState({ phone: data.phone })
                }
                );
                this.connectRabbit();

        }
    }

    renderRestaurantsAndMenu() {
        return (
        <div className="pizza">
            {
                this.state.items &&
                this.state.items.map((item) => {
                    return (
                        <div
                            
                            className="pizza1"
                        >
                            <h2>{item.restaurant_name}</h2>
                            <h4>{item.menu_name}</h4>
                            <h4>{item.category_name}</h4>
                            <h3>{item.item_name}</h3>
                            <p>{item.price}</p>
                            <button className="btn"
                                onClick={() => {
                                    alert(`${item.item_name} added to cart`);
                                }}>Add to basket</button>
                        </div>
                    );
                })
            }</div>
        );
    }

    renderRestaurantNames() {
        return(
        <div className="pizza">
            {
                    this.state.restaurants &&
                    this.state.restaurants.map((item)=>{
                        return (
                            <button className="btn" value={item.name} onClick={() => {
                                this.renderRestaurantMenu(item.id)
                                fetch('http://localhost:8050/restaurant/api/v1/restaurants/menus/items/' + item.id)
        .then(response => response.json())
        .then(data => {
            console.log(data)
                this.state.items = data;
                this.setState({
                    restaurant_items: data
                });
                this.setState({
                    restaurantID: item.id
                });
                this.setState({customerOrderItems:[]});
               this.setState({cart:[]});
        }
        );
                            }}>{item.name}</button>
                        )
                    })
                }
    
    </div>
        );
    }

    renderRestaurantMenu(){
        return (

            <div className="pastOrders">
                    <br />
                    <thead><b>Menu</b></thead>
                    <br />
                    <tbody>
                        <tr>
                            <th>Restaurant Name</th>
                            <th>Menu Name</th>
                            <th>Category Name</th>
                            <th>Item Name</th>
                            <th>Price</th>
                            <th>Action</th>
                        </tr>
                        {this.state.restaurant_items &&
                        this.state.restaurant_items.map((item, index) => (
                        <tr key={index}>
                            <td>{item.restaurant_name}</td>
                            <td>{item.menu_name}</td>
                            <td>{item.category_name}</td>
                            <td>{item.item_name}</td>
                            <td>{item.price}</td>
                                <td><button className="btn"
                                    onClick={() => {
                                        alert(`${item.item_name} added to cart`);
                                        if (this.state.customerID !== '') {
                                            this.addToCart(item);
                                        }
                                    }}>Add to basket</button></td>
                        </tr>
                        ))}
                    </tbody>
                </div>
            );

    }

    addToCart(item_info) {
        if(this.state.restaurantID == item_info.restaurant_id){
           console.log('same restaurant') ;
           
        } else {
            console.log('different restaurant');
            this.setState({
                restaurantID: item_info.restaurant_id
               });
               this.setState({customerOrderItems:[]})
               this.setState({cart:[]})
        }
        this.setState({customerOrder: {'restaurant_id': item_info.restaurant_id,
            'customer_id' : this.state.customerID,
            'delivery_address' : this.state.customerAddress}})
        
        this.state.customerOrderItems.push({"item_id": item_info.id,
        "category_id": item_info.category_id,
        "count": 1,
        "item_price": item_info.price});
        this.state.cart.push(item_info)
        
        this.state.customerOrderItems.map((item) => {
            console.log(item);
        });
        
    }


    loadPastOrders() {
        if (this.state.customerData.id) {
            fetch('http://localhost:8050/customer/api/v1/orders/' + this.state.customerID)
                .then(response => response.json())
                .then(data => {
                    console.log(data)
                    this.state.pastOrders = data;
                    this.setState({
                        pastOrders: data
                    });
                }
                )
        }
    }
    renderPastOrders() {
        return (
            <div className="pastOrders">
                    <br />
                    <thead><b>Past Orders</b></thead>
                    <br />
                    <tbody>
                        <tr>
                            <th>Restaurant Name</th>
                            <th>Order Time</th>
                            <th>Delivery Time</th>
                            <th>Address</th>
                            <th>Price</th>
                            <th>Order Status</th>
                        </tr>
                        {this.state.pastOrders &&
                        this.state.pastOrders.map((item, index) => (
                        <tr key={index}>
                            <td>{item.restaurant_name}</td>
                            <td>{item.order_time}</td>
                            <td>{item.delivery_time}</td>
                            <td>{item.delivery_address}</td>
                            <td>{item.order_price}</td>
                            <td>{item.order_status}</td>
                        </tr>
                        ))}
                    </tbody>
                </div>
            );
    }

    displayCart() {
        return (

            <div className="pastOrders">
                    <br />
                    <thead><b>Cart</b></thead>
                    <br />
                    <tbody>
                        <tr>
                            <th>Restaurant Name</th>
                            <th>Item Time</th>
                            <th>Price</th>
                        </tr>
                        {this.state.cart &&
                        this.state.cart.map((item, index) => (
                        <tr key={index}>
                            <td>{item.restaurant_name}</td>
                            <td>{item.item_name}</td>
                            <td>{item.price}</td>
                        </tr>
                        ))}
                    </tbody>
                </div>
            );
    }

    placeOrder() {
        let requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ 'customerOrder': this.state.customerOrder, 'customerOrderItems': this.state.customerOrderItems})
        };
        fetch('http://localhost:8050/customer/api/v1/order', requestOptions)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                this.setState({customerOrderItems:[]});
               this.setState({cart:[]});
               alert(`Order ID: ${data.id} placed`);
            }
            );
            
    }
    
    
}
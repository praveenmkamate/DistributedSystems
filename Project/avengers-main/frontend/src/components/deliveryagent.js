import React from "react";
import './deliveryagent.css';
export default class DeliveryAgent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            orders: [],
            currentOrders: [],
            name: '',
            address: '',
            phone: '',
            deliveryAgentID:0,
            deliveryAgentData: {},
            isSubmit:false
        };
    }

    componentDidMount() {
        this.render()
    }

    render(){
        return (
            <>
                <div> Hello Agent {this.state.name}</div>
                
                <div>
                    {this.renderPage()}
                </div>
                {/* <div>
                    {this.fetchData()}
                </div> */}
                <div>
                <button className="btn"
                                    onClick={() => {
                                        this.fetchData();                                        
                                    }}>Load Orders</button>
                                    {this.renderOrders()}
                </div>
            </>
        )
    }
    fetchData(){
        if (this.state.deliveryAgentData.id) {
        
        let ordersURL = "http://localhost:8081/delivery/api/v1/delivery-agent/orders/".concat(this.state.deliveryAgentID);
        let currentOrdersURL = "http://localhost:8081/delivery/api/v1/delivery-agent/current-orders/".concat(this.state.deliveryAgentID);
        
        fetch(ordersURL)
        .then(res => res.json())
        .then(
            (result) => {
                this.setState({
                    isLoaded: true,
                    orders: result
                });
            },
            (error) => {
                this.setState({
                    isLoaded: true,
                    error
                });
            }
        )

        fetch(currentOrdersURL)
        .then(res => res.json())
        .then(
            (result) => {
                this.setState({
                    isLoaded: true,
                    currentOrders: result
                });
            },
            (error) => {
                this.setState({
                    isLoaded: true,
                    error
                });
            }
        )
        }
    }

    renderPage() {
        return (
            <div>
            <form onSubmit={(event) => this.fetchAgent(event)}>
                <label htmlFor="fname"> Name: </label>
                <input type="text" id="fname" name="fname"/>
                <label htmlFor="phone"> Phone: </label>
                <input type="text" id="phone" name="phone"/>
                <button type="submit">Login</button>
            </form>
            <br/>
            <br/>
            <div>
          </div>
        </div>
        );
    }

    fetchAgent = (event) =>  {
        event.preventDefault();

        this.setState({
            name: event.target.elements.fname.value
        });

        this.setState({
            phone: event.target.elements.phone.value
        });

        this.state.name= event.target.elements.fname.value;
        this.state.phone= event.target.elements.phone.value;
    if(null != this.state.name || null != this.state.phone || '' != this.state.name || '' != this.state.phone) {
        this.setState({isSubmit: true}, () => {
            console.log(this.state.isSubmit);});
            console.log(this.state.isSubmit);
    }
        if (this.state.isSubmit) {

            let requestOptions = {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ 'name': this.state.name, 'phone': this.state.phone })
            };
            fetch('http://localhost:8081/delivery/api/v1/delivery-agent', requestOptions)
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    this.setState({ deliveryAgentData: data }, () => {
                        console.log(this.state.deliveryAgentData);})
                    this.setState({ deliveryAgentID: data.id }, () => {
                        console.log(this.state.deliveryAgentID);})
                        this.setState({ name: data.name }, () => {
                            console.log(this.state.name);})
                    this.setState({ phone: data.phone })
                    console.log(this.state.phone);})
                }
                

        }
    

    renderOrders() {
        const { error, isLoaded, orders, currentOrders } = this.state;
        if (error) {
            return <div>Error: {error.message}</div>;
        } else {
            return (
                
                <div className="pastOrders">
                    <br />
                    <thead><b>Current Assigned Order</b></thead>
                    <br />
                    <tbody>
                        <tr>
                            <th>Restaurant</th>
                            <th>Customer</th>
                            <th>Delivery Time</th>
                            <th>Address</th>
                            <th>Price</th>
                            <th>Status</th>
                        </tr>
                        {currentOrders.map((item, index) => (
                        <tr key={index}>
                            <td>{item.restaurant}</td>
                            <td>{item.customer_name}</td>
                            <td>{item.delivery_time}</td>
                            <td>{item.delivery_address}</td>
                            <td>{item.order_price}</td>
                            <td>{item.order_status}</td>
                        </tr>
                        ))}
                    </tbody>
                    <br /><br />
                    <thead><b>Past Orders</b></thead>
                    <br />
                    <tbody>
                        <tr>
                            <th>Restaurant</th>
                            <th>Customer</th>
                            <th>Delivery Time</th>
                            <th>Address</th>
                            <th>Price</th>
                            <th>Status</th>
                        </tr>
                        {orders.map((item, index) => (
                        <tr key={index}>
                            <td>{item.restaurant}</td>
                            <td>{item.customer_name}</td>
                            <td>{item.delivery_time}</td>
                            <td>{item.delivery_address}</td>
                            <td>{item.order_price}</td>
                            <td>{item.order_status}</td>
                        </tr>
                        ))}
                    </tbody>
                </div>

                
            )
        }
    }
}
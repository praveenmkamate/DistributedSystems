import React from 'react';
import '../App.css'


export default function Products({ toshow }) {

    const product = [
        { 'name': 'pizza', 'description': 'delicious pizza', 'amount': '5$' },
        { 'name': 'burger', 'description': 'delicious burger', 'amount': '2$' }
    ];



    const [cart, addToCart] = React.useState([]);

    return (
        <div className="pizza">
            {
                product &&
                product.map((item) => {
                    return (
                        <div
                            style={{ backgroundImage: `url(${item.image})` }}
                            className="pizza1"
                        >
                            <h3>{item.name}</h3>
                            <p>{item.description}</p>
                            <p>{item.amount}</p>
                            <button className="btn"
                                onClick={() => {
                                    alert(`${item.name} added to cart`);
                                    addToCart([...cart, item]);
                                }}>Add to basket</button>
                        </div>
                    );
                })
            }
            {toshow ? (
                <div className=" cart-cont">
                    <div className="cart-list">
                        <h1>Cart</h1>
                        {/* cart items */}
                        {cart.map((item) => {
                            return (
                                <div
                                    style={{ backgroundImage: `url(${item.image})` }}
                                    className="cart"
                                >
                                    <h3>{item.name}</h3>
                                    <p>{item.amount}$</p>
                                </div>
                            );
                        })}
                    </div>
                    <div className="total">
                        <h1>Total</h1>
                        <p>{cart.reduce((a, b) => a + b.amount, 0)}$</p>
                        <button>Check out</button>
                    </div>
                </div>
            ) : null}
        </div>
    );
}
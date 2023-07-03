import '../App.css'

export default function Cart() {
    {
        toshow ? (
            <div className=" cart-cont">
                <div className="cart-list">
                    <h1>Cart</h1>
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
        ) : null
    }
}
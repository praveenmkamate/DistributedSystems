import React from "react";
import "./header.css";
import "../App.css"

export default function Header({ showing }) {
    const [show, setShow] = React.useState(false);
    return (
        <div>
            <div className="d">
                <h1 className="header">AVENGERS</h1>
                <button href="/cart" className="cart-btn" onClick={() => {
                    setShow(!show)
                    showing(!show)
                }}>
                    {show ? "close" : "View Cart"}
                </button>
            </div>
        </div>
    );
}
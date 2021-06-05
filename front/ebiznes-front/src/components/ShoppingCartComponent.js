import { getItemCommentsByProductId } from '../services/FetchApi';
import React, { useState } from 'react';
import { Link } from 'react-router-dom';

function ShoppingCartComponent(props) {
    const { cart, addToCart } = props;

    return (
        <div className="shopping-cart">
            Koszyk:
            <pre>
                <ul>
                    {cart.length === 0 && <div>Cart is Empty</div>}
                    {cart && cart.map(obj => (
                        <div >
                            {obj.name}
                        </div>
                    ))}
                </ul>
            </pre>
        </div>
    );
}

export default ShoppingCartComponent;

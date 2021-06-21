import React, { useContext } from 'react';
import { AuthContext } from '../AuthStore'

function ShoppingCartComponent(props) {
    const { cart, addToCart } = props;
    const [state, setState] = useContext(AuthContext);

    const buy = () => {
        console.log("kupiono produkty");
    }

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
            {state.isLoggedIn &&
                <button
                    type="button"
                    className="add-to-cart-button register-bttn"
                    onClick={buy}
                >
                    BUY
                </button>
            }
        </div>
    );
}

export default ShoppingCartComponent;

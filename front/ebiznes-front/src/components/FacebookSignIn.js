import React from 'react'



export default function FacebookSignIn() {

    const responseFacebook = () => {
        window.location.href = "https://ebiznesdckrpb.azurewebsites.net/authenticate/facebook";
    }

    return (
        <div>
            <button onClick={responseFacebook}
                className="add-to-cart-button register-bttn ingsoc fb-bttn"> Login With Facebook </button>
        </div>
    )
}

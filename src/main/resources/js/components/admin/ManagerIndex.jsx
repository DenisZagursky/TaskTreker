import React from "react";
import {Link} from "react-router-dom";
import ButtonLogOut from "../login/ButtonLogOut.jsx";

export default class ManagerIndex extends React.Component {
    render() {
        return (<div>
                <ButtonLogOut/>
                <div className='container'>
                    <h1>Task tracker</h1>
                    <ul>
                        <li><Link to='/project'>project</Link></li>
                        <li><Link to='/users'>users</Link></li>
                    </ul>
                    {this.props.children}
                </div>
            </div>
        )
    }
}

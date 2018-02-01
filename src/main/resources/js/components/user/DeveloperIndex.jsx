import React from "react";
import {Link} from "react-router-dom";
import ButtonLogOut from "../login/ButtonLogOut.jsx"

export default class DeveloperIndex extends React.Component {
    render() {
        return (<div>
                <ButtonLogOut/>
                <div className='container'>
                    <h1>Task tracker</h1>
                    <ul>
                        <li><Link to='/project'>Projects</Link></li>
                        <li><Link to='/filter'>Your tasks</Link></li>
                    </ul>
                    {this.props.children}
                </div>
            </div>
        )
    }
}
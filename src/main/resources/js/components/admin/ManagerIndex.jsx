import React from "react";
import { Link } from 'react-router-dom'

export default class ManagerIndex extends React.Component{
    render() {
        return (
            <div className='container'>
                <h1>App</h1>
                <ul>
                    <li><Link to='/project'>project</Link></li>
                    <li><Link to='/users'>users</Link></li>
                </ul>
                {this.props.children}
            </div>
        )
    }
}
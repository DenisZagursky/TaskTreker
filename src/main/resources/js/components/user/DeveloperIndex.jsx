import React from "react";
import { Link } from 'react-router-dom'

export default class DeveloperIndex extends React.Component{
    render() {
        return (
            <div className='container'>
                <h1>App</h1>
                <ul>
                    <li><Link to='/project'>Projects</Link></li>
                    <li><Link to='/filter'>Your tasks</Link></li>
                </ul>
                {this.props.children}
            </div>
        )
    }
}
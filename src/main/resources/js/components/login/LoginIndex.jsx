import React from "react";
import { Link } from 'react-router-dom'

export default class LoginIndex extends React.Component{
    render() {
        return (
            <div className='container'>
                <h1>Task Tracker</h1>
                <ul>
                    <li><Link to='/'>авторизация</Link></li>
                    <li><Link to='/registration'>Регистрация</Link></li>
                </ul>
                {this.props.children}
            </div>
        )
    }
}
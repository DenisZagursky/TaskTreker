import React, { Component } from 'react'
import jquery from 'jquery'
import toastr from "toastr"

export default class Login extends Component {

    constructor(props){
        super(props)
        this.handleChangeName=this.handleChangeName.bind(this)
        this.handleChangePassword=this.handleChangePassword.bind(this)
        this.handleAdd=this.handleAdd.bind(this)
        this.state={name:'',password:''};
    }

    handleChangeName(event) {
        this.setState({name:event.target.value})
        }
    handleChangePassword(event){
        this.setState({password:event.target.value})
    }
    handleAdd() {
        var self = this
        jquery.ajax({
            url: "http://localhost:8080/login?username="+self.state.name+"&password="+self.state.password,
            type: 'POST',
            success: function (result) {
                debugger
                window.location.reload()
            },
            error: function (xhr, ajaxOptions, thrownError) {
                toastr.error(xhr.status + " Неправильный логин или пароль");
            }
        });
    }

    render() {

        return (
        <div className='row'>
            <div>   <label>Почта <input  type="text" onChange={this.handleChangeName} class="form-control" value={this.state.name}/></label></div>
            <div> <label>Пароль<input type="text" onChange={this.handleChangePassword} class="form-control" value={this.state.password}/></label></div>
            <button className="btn btn-info"
                    onClick={this.handleAdd}
            >войти
            </button>
            <div class="form-group"></div>
            </div>)
    }
}
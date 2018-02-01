import React, { Component } from 'react'
import jquery from 'jquery'
import toastr from "toastr"

export default class Registration extends Component {

    constructor(props){
        super(props)
        this.handleChangeName=this.handleChangeName.bind(this)
        this.handleChangeUsername=this.handleChangeUsername.bind(this)
        this.handleChangePassword=this.handleChangePassword.bind(this)
        this.handleChangeLastName=this.handleChangeLastName.bind(this)
        this.handleChangeCheckBox=this.handleChangeCheckBox.bind(this)
        this.handleAdd=this.handleAdd.bind(this)
        this.state={name:'',lastname:'',username:'',password:'',checkbox:false};
    }


    handleChangeName(event) {
        this.setState({name:event.target.value})
    }
    handleChangeLastName(event)
    {this.setState({lastname:event.target.value})}
    handleChangeCheckBox()
    {   debugger
        this.setState({checkbox:!this.state.checkbox})
    }
    handleChangeUsername(event){
        this.setState({username:event.target.value})
    }
    handleChangePassword(event){
        this.setState({password:event.target.value})
    }

    handleAdd() {
        var self = this
        var role
        debugger
        if (self.state.checkbox==true)
        {role="admin"}
        else {role="user"}
        jquery.ajax({
            url: "http://localhost:8080/registration?username="+self.state.username
            +"&password="+self.state.password
            +"&name="+self.state.name
            +"&lastName="+self.state.lastname
            +"&role="+role,
            type: 'POST',
            success: function (result) {
                toastr.info("Регистрация прошла успешно проверьте почту")
                },
            error: function (xhr, ajaxOptions, thrownError) {
                switch (xhr.status){
                    case 400: toastr.error("данные невалидны"); break;
                    case 502: toastr.error("такой пользователь уже есть"); break;
                }
            }
        });
    }

    render() {

        return (
            <div className='row'>
                <div class="form-group"><label class=""> email : <input onChange={this.handleChangeUsername} class="form-control" type="email" value={this.state.username}/> </label></div>
                <div class="form-group"><label> Password: <input onChange={this.handleChangePassword} class="form-control" type="password" value={this.state.password}/> </label></div>
                <div class="form-group"><label> Name: <input onChange={this.handleChangeName} class="form-control" type="text" value={this.state.name}/> </label></div>
                <div class="form-group"><label> Last Name: <input onChange={this.handleChangeLastName} class="form-control" type="text" value={this.state.lastname}/> </label></div>
                <div class="form-group"><label> Admin:<input  onChange={this.handleChangeCheckBox} class="btn btn -primary" type="checkbox" checked={this.state.checkbox}  /></label></div>
                <div class="form-group"><button class="btn btn-primary" onClick={this.handleAdd} type="submit" value="registration">registration</button></div>
            </div>)
    }
}
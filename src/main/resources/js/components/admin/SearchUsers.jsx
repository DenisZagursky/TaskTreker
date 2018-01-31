import React from "react";
import jQuery from "jquery";
import toastr from "toastr";

export default class SearchUsers extends React.Component {

    constructor(props) {
        super(props);
        this.DrowUsers = this.DrowUsers.bind(this);
        this.handleChangeName = this.handleChangeName.bind(this);
        this.handleChangeLastName = this.handleChangeLastName.bind(this);
        this.state = {users: [], namevalue: '', lastnamevalue: ''};
    }

    DrowUsers() {
        var self = this;
        jQuery.ajax({
                url: "http://localhost:8080/users/?name="
                + self.state.namevalue
                + "&lastName="
                + self.state.lastnamevalue,
                success: function (result) {
                    toastr.info("Запрос успешно выполнен")
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    toastr.error(xhr.responseJSON.message);
                }
            }
        ).then(function (data) {
            self.setState({users: data})
        })
    }

    handleChangeName(event) {
        this.setState({namevalue: event.target.value})
    }

    handleChangeLastName(event) {
        this.setState({lastnamevalue: event.target.value})
    }

    render() {

        return (<div>
                <td><input type="text" onChange={this.handleChangeName} class="form-control"
                           value={this.state.namevalue}/></td>
                <td><input type="text" onChange={this.handleChangeLastName} value={this.state.lastnamevalue}
                           class="form-control"/></td>
                <td>
                    <button className="btn btn-outline-info" onClick={this.DrowUsers}>find</button>
                </td>
                <UserTable users={this.state.users}/>
            </div>
        )
    }
}

class User extends React.Component {
    constructor(props) {
        super(props);
    }


    render() {
        return (
            <tr>
                <td>{this.props.user.username}</td>
                <td>{this.props.user.name}</td>
                <td>{this.props.user.lastName}</td>
            </tr>
        );
    }

}

class UserTable extends React.Component {
    render() {
        var rows = [];
        this.props.users.forEach(function (user) {
            rows.push(<User user={user}/>);
        });
        return (
            <table className="table table-striped">
                <thead>
                <tr>
                    <th>Email</th>
                    <th>Name</th>
                    <th>LastName</th>
                </tr>
                </thead>
                <tbody>{rows}</tbody>
            </table>);
    }
}
;
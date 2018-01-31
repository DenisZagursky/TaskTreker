import React from "react";
import jquery from "jquery";
import toastr from "toastr";

export default class DrawComments extends React.Component {
    constructor(props) {
        super(props);
        this.state = {comments: []};
        this.loadData = this.loadData.bind(this)
        this.onChange = this.onChange.bind(this)
    }

    componentDidMount() {
        this.loadData();
    }

    loadData() {
        var self = this;
        var position=window.location.href.lastIndexOf("/");
        jquery.ajax({url: "http://localhost:8080/comments/" + window.location.href.slice(position)}).then(function (data) {
            self.setState({comments: data})

        })
    }

    onChange() {
        this.loadData()
    }

    render() {
        return (<div>
                <CommentsTable onChange={this.onChange} comments={this.state.comments}/>
                <AddComment onChange={this.onChange}/>
            </div>
        )
    }

}
class CommentsTable extends React.Component {
    constructor(props) {
        super(props);
        this.compareId = this.compareId.bind(this)
    }

    compareId(commentA, commentB) {
        debugger
        return commentA.props.comment.id - commentB.props.comment.id;
    }

    render() {
        var self = this
        var rows = [];
        debugger
        this.props.comments.forEach(function (comment) {
            rows.push(<Comment onChange={self.props.onChange} comment={comment}/>);
        });
        debugger
        rows.sort(this.compareId)
        return (
            <table className="table table-striped">
                <thead>
                <tr>
                    <th>имя</th>
                    <th>фамилия</th>
                    <th>Комментарий</th>
                    <th>обновить</th>
                </tr>
                </thead>
                <tbody>{rows}
                </tbody>

            </table>);
    }
}
class Comment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {name: '', lastname: '', newcomment: '', buttonvalue: 'delete', canchange: ''};
        this.handleChangeComment = this.handleChangeComment.bind(this);
        this.loadUser = this.loadUser.bind(this);
        this.handleUpdateComment = this.handleUpdateComment.bind(this)
    }

    componentDidMount() {
        this.loadUser();
    }

    loadUser() {
        var self = this
        jquery.ajax({
            url: "http://localhost:8080/comments/user/" + this.props.comment.id,
            type: 'GET',
            success: function (result) {
                self.setState({name: result.name})
                self.setState({lastname: result.lastname})
                self.setState({canchange: result.canchange})
            }
        })
    }

    handleChangeComment(event) {
        this.setState({newcomment: event.target.value})
        if (event.target.value == "") {
            this.setState({buttonvalue: 'delete'})
        } else {
            this.setState({buttonvalue: 'update'})
        }
    }

    handleUpdateComment() {
        var self = this
        if (self.state.buttonvalue == "update") {
            jquery.ajax({
                url: "http://localhost:8080/comments/" + self.props.comment.id + "/" + self.state.newcomment,
                type: 'PUT',
                success: function (result) {
                    toastr.info("Комментарий успешно обновлен")
                    self.props.onChange();
                }
            })
        } else {
            jquery.ajax({
                url: "http://localhost:8080/comments/" + self.props.comment.id,
                type: 'DELETE',
                success: function (result) {
                    toastr.info("Комментарий успешно удален")
                    self.props.onChange();
                }
            })
        }
    }

    render() {

        if (this.state.canchange == "true") {
            return (
                <tr>
                    <td >{this.state.name}</td>
                    <td>{this.state.lastname}</td>
                    <td> {this.props.comment.description}</td>
                    <td>
                        <button onClick={this.handleUpdateComment}
                                className="btn btn-info">{this.state.buttonvalue}</button>
                    </td>
                    <td><input type="text"
                               onChange={this.handleChangeComment}
                               value={this.state.newcomment}
                               class="form-control"
                    /></td>
                </tr>
            );
        } else {
            return (
                <tr>
                    <td >{this.state.name}</td>
                    <td>{this.state.lastname}</td>
                    <td> {this.props.comment.description}</td>
                </tr>
            )
        }
    }
}
class AddComment extends React.Component {
    constructor(props) {
        super(props);
        this.handleAdd = this.handleAdd.bind(this);
        this.handleChangeDescription = this.handleChangeDescription.bind(this);
        this.state = {description: '', name: '', lastname: ''};
        this.loadUser = this.loadUser.bind(this);
    }
componentDidMount(){
        this.loadUser();
}

    handleAdd() {
        var self = this
        var position=window.location.href.lastIndexOf("/");
        jquery.ajax({
            url: "http://localhost:8080/comments/"+window.location.href.slice(position)+"/"+self.state.description,
            type: 'POST',
            success: function (result) {
                toastr.info("коментарий успешно добавлен")
                self.props.onChange();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                toastr.error(xhr.status + " Ошибка при создании таска");
            }
        });
    }

    loadUser() {
        var self = this
        jquery.ajax({url: "http://localhost:8080/auth"}).then(function (data) {
            self.setState({name: data.name, lastname:data.lastname})

        })
    }

    handleChangeDescription(event) {
        this.setState({description: event.target.value})
    }

    render() {
        return (
            <tr><td>{this.state.name}</td>
                <td>{this.state.lastname}</td>
                <td><input type="text"
                           onChange={this.handleChangeDescription}
                           value={this.state.description}
                           class="form-control"
                /></td>
                <td>
                    <button className="btn btn-info"
                            onClick={this.handleAdd}
                    >Добавить коментарий
                    </button>
                </td>
            </tr>

        );
    }
}

import React,{ReactPropTypes} from 'react'
import jquery from "jquery";
import toastr from "toastr";
import { Link,BrowserRouter } from 'react-router-dom'


export default class DrawTasksAdmin extends React.Component {
    constructor(props) {
        super(props);
        this.state = {tasks: []};
        this.loadData=this.loadData.bind(this)
        this.onChange=this.onChange.bind(this)
    }
    componentDidMount() {
       this.loadData();
    }
    loadData(){
        var position=window.location.href.lastIndexOf("/");
        var self = this;
        jquery.ajax({url: "http://localhost:8080/tasks/"+window.location.href.slice(position)}).then(function (data) {
            self.setState({tasks: data})

        })
    }
    onChange(){
        this.loadData();
    }
    render() {
        return (<div>
                <TaskTable tasks={this.state.tasks}/>
                <AddTask onChange={this.onChange}/>
            </div>
        )
    }

}
class TaskTable extends React.Component{
    constructor(props) {
        super(props);

    }

    render() {
        var rows = [];
        this.props.tasks.forEach(function (task) {
            rows.push(<Task task={task}/>);
        });
        return (
            <table className="table table-striped">
                <thead>
                <tr>
                    <th>Название таска</th>
                    <th>Описание</th>
                    <th>Статус</th>
                    <th>Добавление сотрудника</th>
                    <th>Почта сотрудника</th>
                </tr>
                </thead>
                <tbody>{rows}
                </tbody>

            </table>);
    }
}
class Task extends React.Component{
    constructor(props) {
        super(props);
        this.handleAdd = this.handleAdd.bind(this);
        this.state = {usernamevalue: '', status:this.props.task.taskStatus};
        this.handleChangeUsername=this.handleChangeUsername.bind(this);
        this.handleChangeStatus=this.handleChangeStatus.bind(this)
    }

    handleChangeUsername(event) {
        this.setState({usernamevalue: event.target.value})
    }
    handleChangeStatus(event){
        this.setState({status: event.target.value})
        var self=this
        jquery.ajax({
            url:"http://localhost:8080/tasks/status/"+this.props.task.id+"/"+event.target.value,
            type: 'POST',
            success: function (result) {
                toastr.info("статус успешно изменен")
            },
            error:function (xhr) {
                toastr.error(xhr.status+"Ошибка в изменении статуса")
            }
        })
    }
    handleAdd() {
        var self = this
        jquery.ajax({
            url: "http://localhost:8080/tasks/" + this.props.task.id + "/" + self.state.usernamevalue,
            type: 'POST',
            success: function (result) {
                toastr.info("пользователь успешно добавлен")
            },
            error: function (xhr, ajaxOptions, thrownError) {
                toastr.error(xhr.status + " Пользователя добавить нельзя или его не существует в этом проекте");
            }
        });
    }

    render() {
        return (
            <tr>
                <td >
                    <Link to={"/comments/"+this.props.task.id}>
                        {this.props.task.name}</Link></td>
                <td>{this.props.task.description}</td>
                <td><select  onChange={this.handleChangeStatus}
                            value={this.state.status} >
                    <option disabled>{this.props.task.taskStatus}</option>
                    <option>WAITING</option>
                    <option>IMPLEMENTATION</option>
                    <option>VERIFYING</option>
                    <option>RELEASING</option>
                </select>
                </td>
                <td>
                    <button className="btn btn-info" onClick={this.handleAdd}>Добавить</button>
                </td>
                <th><input type="text" onChange={this.handleChangeUsername}
                           value={this.state.usernamevalue}
                           class="form-control"
                           defaultValue={"someName"}/></th>
            </tr>
        );

    }

}
class AddTask extends React.Component{
    constructor(props) {
        super(props);
        this.handleAdd = this.handleAdd.bind(this);
        this.handleChangeTaskname = this.handleChangeTaskname.bind(this);
        this.handleChangeTaskDescription = this.handleChangeTaskDescription.bind(this);
        this.state = {taskname: '', taskdescription: ''};
    }

    handleAdd() {
        var self = this
        var position=window.location.href.lastIndexOf("/");
        debugger
        jquery.ajax({
            url: "http://localhost:8080/tasks/"+window.location.href.slice(position)+
            "/"+self.state.taskname+"/"+self.state.taskdescription,
            type: 'POST',
            success: function (result) {
                toastr.info("таск успешно создан")
                self.props.onChange();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                toastr.error(xhr.status + " Ошибка при создании таска");
            }
        });
    }

    handleChangeTaskname(event) {
        this.setState({taskname: event.target.value})
    }

    handleChangeTaskDescription(event) {
        this.setState({taskdescription: event.target.value})
    }

    render() {
        return (
            <tr>
                <td ><input type="text"
                            onChange={this.handleChangeTaskname}
                            value={this.state.taskname}
                            class="form-control"
                /></td>
                <td><input type="text"
                           onChange={this.handleChangeTaskDescription}
                           value={this.state.taskdescription}
                           class="form-control"
                /></td>
                <td>
                    <button className="btn btn-info"
                            onClick={this.handleAdd}
                    >Добавить таск
                    </button>
                </td>
            </tr>

        );
    }
}
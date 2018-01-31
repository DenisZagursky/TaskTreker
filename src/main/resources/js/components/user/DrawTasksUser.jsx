import React,{ReactPropTypes} from 'react'
import jquery from "jquery";
import toastr from "toastr";
import { Link,BrowserRouter } from 'react-router-dom'


export default class DrawTasksUser extends React.Component {
    constructor(props) {
        super(props);
        this.state = {tasks: []};
        this.loadData=this.loadData.bind(this)
    }
    componentDidMount() {
        this.loadData();
    }
    loadData(){
        var self = this;
        var position=window.location.href.lastIndexOf("/");
        jquery.ajax({url: "http://localhost:8080/tasks/"+window.location.href.slice(position)}).then(function (data) {
            self.setState({tasks: data})

        })
    }
    render() {
        return (<div>
                <TaskTable tasks={this.state.tasks}/>
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
        this.handleChangeStatus=this.handleChangeStatus.bind(this)
        this.state = { status:this.props.task.taskStatus};
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

    render() {
        debugger
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
              </tr>
        );

    }

}
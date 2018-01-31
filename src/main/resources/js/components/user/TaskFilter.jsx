import React from "react";
import jquery from 'jquery'
import { Link,BrowserRouter } from 'react-router-dom'

export default class TaskFilter extends React.Component {
    constructor(props) {
        super(props);
        this.state = {tasks: []};
        this.loadData = this.loadData.bind(this)
    }

    componentDidMount() {
        this.loadData();
    }

    loadData() {
        var self = this;
        jquery.ajax({url: "http://localhost:8080/tasks/filter"}).then(function (data) {
            self.setState({tasks: data})

        })
    }

    render() {
        return (<div>
                <FilterTable tasks={this.state.tasks}/>
            </div>
        )
    }
}
class FilterTable extends React.Component {
    render() {
        var rows = [];
        this.props.tasks.forEach(function (data) {
            rows.push(<Information information={data}/>);
        });
        return (
            <table className="table table-striped">
                <thead>
                <tr>
                    <th>project</th>
                    <th>task</th>
                    <th>status</th>
                </tr>
                </thead>
                <tbody>{rows}</tbody>
            </table>);
    }
}
class Information extends React.Component {
    constructor(props) {
        super(props);
    }


    render() {
        return (
            <tr>
                <td><Link to={"/task/" + this.props.information.projectid}>
                    {this.props.information.project}</Link></td>
                <td><Link to={"/comments/" + this.props.information.taskid}>
                    {this.props.information.task}</Link></td>
                <td>{this.props.information.status}</td>
            </tr>
        );
    }
}
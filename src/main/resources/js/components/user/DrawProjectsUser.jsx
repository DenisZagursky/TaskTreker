import React from "react";
import jquery from "jquery";
import toastr from "toastr";
import {Link} from "react-router-dom";


export default class DrawProjectsUser extends React.Component {

    constructor(props) {
        super(props);
        this.state = {projects: []};
        this.loadData = this.loadData.bind(this)
    }

    componentDidMount() {
        this.loadData();
    }

    loadData() {

        var self = this;
        jquery.ajax({url: "http://localhost:8080/projects"}).then(function (data) {
            self.setState({projects: data})

        })
    }

    onChange() {
        this.loadData();
    }

    render() {
        return (<div>
                <ProjectTable projects={this.state.projects}/>
            </div>
        )
    }
}

class ProjectTable extends React.Component {
    constructor(props) {
        super(props);

    }

    render() {
        var rows = [];
        this.props.projects.forEach(function (project) {
            rows.push(<Project project={project}/>);
        });
        return (
            <table className="table table-striped">
                <thead>
                <tr>
                    <th>Название проекта</th>
                    <th>Описание</th>
                </tr>
                </thead>
                <tbody>{rows}
                </tbody>

            </table>);
    }
}


class Project extends React.Component {

    constructor(props) {
        super(props);

    }


    render() {
        return (
            <tr>
                <td >
                    <Link to={"/task/" + this.props.project.id}>
                        {this.props.project.name}</Link></td>
                <td>{this.props.project.description}</td>
            </tr>
        );

    }

}

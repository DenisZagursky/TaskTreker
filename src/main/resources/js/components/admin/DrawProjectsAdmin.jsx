import React,{ReactPropTypes} from 'react'
import jquery from "jquery";
import toastr from "toastr";
import { Link, } from 'react-router-dom'


export default class DrawProjectsAdmin extends React.Component {

    constructor(props) {
        super(props);
        this.state = {projects: []};
        this.loadData=this.loadData.bind(this)
        this.onChange=this.onChange.bind(this)
    }

    componentDidMount() {
       this.loadData();
    }

    loadData(){

        var self = this;
        jquery.ajax({url: "http://localhost:8080/projects"}).then(function (data) {
            self.setState({projects: data})

        })
    }
    onChange(){
        this.loadData();
    }

    render() {
        return (<div>
                <ProjectTable projects={this.state.projects}/>
                <AddProject onChange={this.onChange}/>
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
                    <th>Добавление сотрудника</th>
                    <th>Почта сотрудника</th>
                </tr>
                </thead>
                <tbody>{rows}
                </tbody>

            </table>);
    }
}
;

class Project extends React.Component {

    constructor(props) {
        super(props);
        this.handleAdd = this.handleAdd.bind(this);
        this.handleChangeUsername=this.handleChangeUsername.bind(this);
        this.state = {usernamevalue: ''};
    }

    handleChangeUsername(event) {
        this.setState({usernamevalue: event.target.value})
    }

    handleAdd() {
        var self = this
        jquery.ajax({
            url: "http://localhost:8080/projects/" + this.props.project.id + "/" + self.state.usernamevalue,
            type: 'POST',
            success: function (result) {
                toastr.info("пользователь успешно добавлен")
                self.setState({username:''})
            },
            error: function (xhr, ajaxOptions, thrownError) {
                toastr.error(xhr.status + " Пользователя добавить нельзя или его не существует");
            }
        });
    }

    render() {
        return (
            <tr>
                <td >
                    <Link to={"/task/"+this.props.project.id}>
                        {this.props.project.name}</Link></td>
                <td>{this.props.project.description}</td>
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

class AddProject extends React.Component {
    constructor(props) {
        super(props);
        this.handleAdd = this.handleAdd.bind(this);
        this.handleChangeProjectname = this.handleChangeProjectname.bind(this);
        this.handleChangeProjectDescription = this.handleChangeProjectDescription.bind(this);
        this.state = {projectname: '', projectdescription: ''};
    }

    handleAdd() {
        var self = this
        jquery.ajax({
            url: "http://localhost:8080/admin/projects/" + self.state.projectname + "/" + self.state.projectdescription,
            type: 'POST',
            success: function (result) {
                toastr.info("Проект успешно создан")
                self.props.onChange();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                toastr.error(xhr.status + " Ошибка при создании проекта");
            }
        });
    }

    handleChangeProjectname(event) {
        this.setState({projectname: event.target.value})
    }

    handleChangeProjectDescription(event) {
        this.setState({projectdescription: event.target.value})
    }

    render() {
        return (
            <tr>
                <td ><input type="text"
                            onChange={this.handleChangeProjectname}
                            value={this.state.projectname}
                            class="form-control"
                /></td>
                <td><input type="text"
                           onChange={this.handleChangeProjectDescription}
                           value={this.state.projectdescription}
                           class="form-control"
                /></td>
                <td>
                    <button className="btn btn-info"
                            onClick={this.handleAdd}
                    >Добавить проект
                    </button>
                </td>
            </tr>

        );
    }
}


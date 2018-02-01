import React from "react";
import jquery from "jquery";


export default class ButtonLogOut extends React.Component {

    constructor(props) {
        super(props);
        this.handleAdd = this.handleAdd.bind(this);
    }

    handleAdd() {
        jquery.ajax({
            url: "http://localhost:8080/login?logout/",
            type: 'POST',
            success: function (result) {
                window.location.assign("http://localhost:8080/")
            },
            error: function (xhr, ajaxOptions, thrownError) {
                toastr.error(xhr.status + " Ошибка при выходе");
            }
        });
    }


    render() {
        return ( <div>
                <button className="btn btn-info"
                        onClick={this.handleAdd}
                >LOG OUT
                </button>
            </div>
        );
    }
}
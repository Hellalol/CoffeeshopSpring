$(document).ready(function () {

    fetch('http://localhost:8080/customers/all')
        .then(res => res.json())
        .then(data => {
            showCustomers(data);
        }).catch(error => console.error(error));

    function showCustomers(data) {
        let listCustomers = ``;
        data.customer.forEach(elm => {
            listCustomers +=
            ` <tr>
                <td>${elm.firstName}</td>
                <td>${elm.lastName}</td>
                <td>${elm.email}</td>
                <td><button></td>
            </tr> `

        });

        $('#shoes').html(listCustomers);


    }

});
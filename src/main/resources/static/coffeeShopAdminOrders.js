$(document).ready(function () {
    fetch('MOCK_DATA.json')
        .then(res => res.json())
        .then(data => {
            product(data);
        }).catch(error => console.error(error));

    $('.collapse').collapse();

    function product(data) {
        let productList = ``;
        data.product.forEach(elm => {
            productList +=
                `<tr>
            <td scope="row">Brand: ${elm.productName}</td>
            <td scope="row">Price: ${elm.basePrice}</td>         
            <td scope="row">Description: ${elm.description}</td>
        </tr>`

        });

        $('.stuffy').append(productList);

    }

});
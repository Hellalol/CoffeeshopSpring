$(document).ready(function () {
    fetch('MOCK_PURCHASE.json')
        .then(res => res.json())
        .then(data => {
            product(data);
        }).catch(error => console.error(error));

    $('.collapse').collapse();

    // const unique = new Map(product(data).map(obj => [obj.id, obj]));
    //
    //   const uniques = Array.from(unique.values());


    function product(data) {
        let productList = ``;
        let productList2 = ``;
        let groupById = [];

        let placeInArray = 0;
        for (let i = 0; i < data.purchase.length; i++){
            if(i === 0) {
                groupById[placeInArray] += data.purchase[i].productName + ","
                    + data.purchase[i].basePrice + "," + data.purchase[i].description + ",";
            }else{
                if(data.purchase[i].id === data.purchase[(i - 1)].id){
                        groupById[(placeInArray)] += data.purchase[i].productName + ","
                        + data.purchase[i].basePrice + "," + data.purchase[i].description;
                }else{
                    placeInArray++;
                    groupById[(placeInArray)] += data.purchase[i].productName + ","
                    + data.purchase[i].basePrice + "," + data.purchase[i].description;
                }
            }
        }


        groupById.forEach(elm => {

            productList +=  `<div class="card">
        <div class="card-header ,heading">
            <h2 class="mb-0">
                <button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse"
                        data-target="#collapse${elm.id}" aria-expanded="false" aria-controls="collapse${elm.id}">
                    Order ${elm.id}
                </button>
            </h2>
        </div>`


        });

        data.purchase.forEach(elm => {

                productList2 += `
       <div id="collapse${elm.id}" class="collapse" aria-labelledby="heading" data-parent="#test">
            <div class="card-body stuffy">
                <tr>
                 <td scope="row">Brand: ${elm.productName}</td>
                 <td scope="row">Price: ${elm.basePrice}</td>         
                 <td scope="row">Description: ${elm.description}</td>
                </tr>
            </div>
        </div>
    </div> `;


        });

        $('.stuffy').append(productList,productList2);

    }

});
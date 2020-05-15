// fetch product details from JSON, currently using a dummy url
fetch("dummyproducts.json")
    .then(response => response.json())
    .then(products => {
        displayProduct(products[0]);
    })
    .catch(error => console.error(error));

function displayProduct(product) {
    let productDescription = document.getElementById("product-description");
    productDescription.innerHTML = `<h2>${product.name}</h2><p>${product.description}</p>`;
}
const urlParams = new URLSearchParams(window.location.search);
// TODO Rewrite to something less fragile, using the actual id rather than list index
const productId = urlParams.has("id") ? Number(urlParams.get("id")) : 0;

// fetch product details from JSON, currently using a dummy url
fetch("dummyproducts.json")
    .then(response => response.json())
    .then(products => {
        displayProduct(products[productId]);
    })
    .catch(error => console.error(error));

function displayProduct(product) {
    let productDescription = document.getElementById("product-description");
    productDescription.innerHTML = `<h2>${product.name}</h2><p>${product.description.replace(/\n/g, '</p><p>')}</p>`;

    let productImage = document.getElementById("product-image");
    productImage.innerHTML = `<img src="${product.imgPath}">`;
}
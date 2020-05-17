// TODO Rewrite to something less fragile, using the actual id rather than list index and so on
const urlParams = new URLSearchParams(window.location.search);
const productId = urlParams.has("id") ? Number(urlParams.get("id")) : 1;

// fetch product details from JSON, currently using a dummy url
fetch("dummyproducts.json")
    .then(response => response.json())
    .then(products => {
        displayProduct(products[productId - 1]);
    })
    .catch(error => console.error(error));

function displayProduct(product) {
    let productDescription = document.getElementById("product-description");
    productDescription.innerHTML = `<h2>${product.productName}</h2><p>${product.description}</p><p class="price">Price: ${product.basePrice} SEK</p>`;

    let productImage = document.getElementById("product-image");
    productImage.innerHTML = `<img src="${product.imgPath}">`;
}
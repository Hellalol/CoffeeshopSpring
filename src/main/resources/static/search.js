// fetch objects from JSON, currently using a dummy url
fetch("dummyproducts.json")
    .then(response => response.json())
    .then(products => {
        displayAllProducts(products);
    })
    .catch(error => console.error(error));

function displayAllProducts(products) {
    let productContainer = document.getElementById("products");
    products.forEach(product => productContainer.appendChild(getProductListing(product)));
}

function getProductListing(product) {
    let gridItem = document.createElement("div");
    gridItem.classList.add("col-md-3", "col-sm-6");

    let card = document.createElement("div");
    card.className = "card";
    card.innerHTML = `<div class="card-img-top"><img src="${product.imgPath}" alt="dummy alt text""> </div>`;

    let productContent = document.createElement("div");
    productContent.className = "card-body";
    productContent.innerHTML =
        `<h3 class="card-title"><a href="product-details.html?id=${product.id}">${product.name}</a></h3>
        <p class="card-text price">Price: ${product.price} SEK</p>`;

    let button = document.createElement("button");
    button.className = "btn add-to-cart";
    button.textContent = "Add to cart";
    button.addEventListener("click", function (e) {
        // Adding to cart
    });

    productContent.appendChild(button);
    card.appendChild(productContent);
    gridItem.appendChild(card);
    return gridItem;
}
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
    let card = document.createElement("div");
    card.classList.add("col-md-3", "col-sm-6");

    let innerCard = document.createElement("div");
    innerCard.className = "product-grid";
    innerCard.innerHTML = `<div class="product-img"><img src="${product.imgPath}" alt="dummy alt text""> </div>`;

    let productContent = document.createElement("div");
    productContent.className = "product-description";
    productContent.innerHTML =
        `<h3 class="title"><a href="#">${product.name}</a></h3>
        <p class="price">Price: ${product.price} SEK</p>`;

    let button = document.createElement("button");
    button.className = "btn add-to-cart";
    button.textContent = "Add to cart";
    button.addEventListener("click", function (e) {
        // Adding to cart
    });

    productContent.appendChild(button);
    innerCard.appendChild(productContent);
    card.appendChild(innerCard);
    return card;
}
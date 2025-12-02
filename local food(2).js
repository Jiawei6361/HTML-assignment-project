let images = ["https://a.travel-assets.com/findyours-php/viewfinder/images/res70/473000/473015-Kuala-Lumpur.jpg",
 "https://images.unsplash.com/photo-1711845845032-c8c621a31b4b?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 
 "https://static.vecteezy.com/system/resources/previews/001/964/919/large_2x/kuala-lumpur-malaysia-2020-night-view-of-petronas-twin-towers-free-photo.jpg", 
 "https://live.staticflickr.com/3111/3247230876_8b12a45e3d_h.jpg"]; let currentIndex = 0;

function changeBackground() {
    let bgDiv = document.getElementById("background");
    currentIndex = (currentIndex + 1) % images.length; 
    bgDiv.style.backgroundImage = `url('${images[currentIndex]}')`;
}


setInterval(changeBackground, 5000);

window.onload = function() {
    document.getElementById("background").style.backgroundImage = `url('https://a.travel-assets.com/findyours-php/viewfinder/images/res70/473000/473015-Kuala-Lumpur.jpg')`;
};
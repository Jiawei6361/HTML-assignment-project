let images = ["https://dynamic-media-cdn.tripadvisor.com/media/photo-o/25/dc/17/bb/signature-nasi-campur.jpg?w=900&h=500&s=1",
 "https://www2.tocoo.jp/must-visit/assets/img/night-view/spots/20.jpg", 
 "https://media.timeout.com/images/103930894/image.jpg", 
 "https://images.squarespace-cdn.com/content/v1/52f50c98e4b08f72cd95680a/1550102880160-PXB2NE4PRMMZDK5MX7QD/LSNY_Night_City_Views-2.jpg"]; let currentIndex = 0;

function changeBackground() {
    let bgDiv = document.getElementById("background");
    currentIndex = (currentIndex + 1) % images.length; 
    bgDiv.style.backgroundImage = `url('${images[currentIndex]}')`;
}


setInterval(changeBackground, 5000);

window.onload = function() {
    document.getElementById("background").style.backgroundImage = `url('https://media.timeout.com/images/103930894/image.jpg')`;
};
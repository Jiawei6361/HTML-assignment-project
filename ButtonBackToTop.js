// Back To Top BUTTON
window.onscroll = function () {
    var button = document.getElementById("backToTop");
    if (document.documentElement.scrollTop > 300) {
        button.style.display = "block"; // Scroll more than 300px, show button
    } else {
        button.style.display = "none"; // Otherwise hidden
    }
};

function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: "smooth" // Smooth scrolling
    });
}
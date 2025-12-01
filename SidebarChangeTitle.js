const sections = document.querySelectorAll("section");
const navLinks = document.querySelectorAll(".sidebar-link");

const observer = new IntersectionObserver(entries => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            navLinks.forEach(link => {
                link.classList.remove("active");
                if (link.getAttribute("href").substring(1) === entry.target.id) {
                    link.classList.add("active");
                }
            });
        }
    });
}, { threshold: 0.5 });

sections.forEach(section => {
    observer.observe(section);
});


// Smooth scrolling effect
document.querySelectorAll(".sidebar-link").forEach(link => {
    link.addEventListener("click", function (event) {
        event.preventDefault(); // Prevent default redirection

        const targetId = this.getAttribute("href").substring(1); //Gets the target section ID (removes the #)
        const targetElement = document.getElementById(targetId); // Finds the corresponding element in the DOM

        if (targetElement) {
            targetElement.scrollIntoView({
                behavior: "smooth", // Smooth scrolling
                block: "start" // Scrolls to align the section at the top of the viewport
            });
        }
    });
});






// Overview
document.addEventListener("DOMContentLoaded", function () {
    const observer = new IntersectionObserver(entries => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add("visible");
            } else {
                entry.target.classList.remove("visible"); // Make the animation trigger repeatedly
            }
        });
    }, { threshold: 0.3 });

    const section = document.querySelector(".MainPageOverview");
    observer.observe(section);
});
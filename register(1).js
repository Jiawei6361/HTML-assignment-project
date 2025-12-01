const images = [
    'https://business.stthomas.edu/_media-library/degrees-programs/undergraduate/student-experience/images/stthomas-student-alumni-event.jpg',
    'https://www.collegetransitions.com/wp-content/uploads/2023/07/blog-100BestClubsHighSchool-1460x822-1.webp',
    'https://www.signupgenius.com/cms/socialMediaImages/how-to-start-a-college-club-1260x630.jpg',
    'https://kec.edu.np/wp-content/uploads/2024/08/IMGL3841-scaled.jpg'
];

let index = 0;

function changeBackground() {
    document.body.style.backgroundImage = `url('${images[index]}')`;
    index = (index + 1) % images.length;
}

setInterval(changeBackground, 5000);
changeBackground();

document.getElementById("registerForm").addEventListener("submit", function(event) {
    event.preventDefault(); 
    window.location.href = "success.html"; 
});
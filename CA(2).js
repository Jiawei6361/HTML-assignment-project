let index=0
const images= document.querySelectorAll('.slider img');
const totalimages=images.length;

function showNextimg(){
    index++;
    if(index>= totalimages){
        index=0;
    }
    document.querySelector('.slider').style.transform= `translateX(-${index* 75}%)`;
}
setInterval(showNextimg,3000)
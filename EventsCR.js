document.addEventListener('DOMContentLoaded', function() {
    const questions = document.querySelectorAll('.faq-question');

    questions.forEach(function(question) {
        question.addEventListener('click', function() {
            const parent = this.parentElement;
            
            // Collapse all expanded items first
            document.querySelectorAll('.faq-item').forEach(function(item) {
                if (item !== parent) {
                    item.classList.remove('active');
                }
            });

            // Toggle the expanded state of the current item
            parent.classList.toggle('active');
        });
    });
});
document.addEventListener('click', function(event) {
    const profile = document.querySelector('.profile');
    const isClickInside = profile.contains(event.target);

    if (!isClickInside) {
        profile.classList.remove('active');
    }
});


function getCsrfToken() {
    const csrfMetaTag = document.querySelector('meta[name="_csrf"]');
    return csrfMetaTag ? csrfMetaTag.getAttribute('content') : '';
}
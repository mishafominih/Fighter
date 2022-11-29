let condition = true;

function forwards() {
    anime({
        targets: '.menu-small',
        translateX: ['-100%', '0'],
        backgroundColor: ['white', 'black'],
        color: 'white',
        easing: 'easeInOutQuad',
        direction: 'alternate',
        duration: 500,
        loop: false
    });

    anime({
        targets: '.menu_small_icon',
        rotate: 180,
        easing: 'easeInOutQuad',
        direction: 'alternate',
        duration: 500,
        loop: false
    });
    anime({
        targets: '.stick',
        rotate: 180,
        easing: 'easeInOutQuad',
        direction: 'alternate',
        duration: 500,
        loop: false
    });
    condition = false
}

function backwards() {
    anime({
        targets: '.menu-small',
        translateX: ['0', '-100%'],
        easing: 'easeInOutQuad',
        direction: 'alternate',
        duration: 500,
        loop: false
    });
    anime({
        targets: '.menu_small_icon',
        rotate: 0,
        easing: 'easeInOutQuad',
        direction: 'alternate',
        duration: 500,
        loop: false
    });
    anime({
        targets: '.stick',
        rotate: 0,
        easing: 'easeInOutQuad',
        direction: 'alternate',
        duration: 500,
        loop: false
    });
    condition = true;
}

$('.menu_small_icon').click(function () {
    if (condition) {
        forwards();
    } else {
        backwards();
    }
});

let start = 0;
let end = 0;



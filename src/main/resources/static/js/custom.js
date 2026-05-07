
  (function ($) {
  
  "use strict";

    // MENU
    $('.navbar-collapse a').on('click',function(){
      $(".navbar-collapse").collapse('hide');
    });
    
    // CUSTOM LINK
    $('.smoothscroll').click(function(){
      var el = $(this).attr('href');
      var elWrapped = $(el);
      var header_height = $('.navbar').height();
  
      scrollToDiv(elWrapped,header_height);
      return false;
  
      function scrollToDiv(element,navheight){
        var offset = element.offset();
        var offsetTop = offset.top;
        var totalScroll = offsetTop-navheight;
  
        $('body,html').animate({
        scrollTop: totalScroll
        }, 300);
      }
    });

    $(window).on('scroll', function(){
      function isScrollIntoView(elem, index) {
        var docViewTop = $(window).scrollTop();
        var docViewBottom = docViewTop + $(window).height();
        var elemTop = $(elem).offset().top;
        var elemBottom = elemTop + $(window).height()*.5;
        if(elemBottom <= docViewBottom && elemTop >= docViewTop) {
          $(elem).addClass('active');
        }
        if(!(elemBottom <= docViewBottom)) {
          $(elem).removeClass('active');
        }
        var MainTimelineContainer = $('#vertical-scrollable-timeline')[0];
        var MainTimelineContainerBottom = MainTimelineContainer.getBoundingClientRect().bottom - $(window).height()*.5;
        $(MainTimelineContainer).find('.inner').css('height',MainTimelineContainerBottom+'px');
      }
      var timeline = $('#vertical-scrollable-timeline li');
      Array.from(timeline).forEach(isScrollIntoView);
    });
  
  })(window.jQuery);


    // // Get the current page URL
    // const currentLocation = window.location.pathname;

    // // Get all the nav links
    // const navLinks = document.querySelectorAll('.navbar-nav .nav-link');

    // // Loop through the nav links and add the active class to the current page
    // navLinks.forEach(link => {
    //     if (link.href.includes(currentLocation)) {
    //         link.classList.add('active'); // Add 'active' class
    //     }
    // });




    document.addEventListener('DOMContentLoaded', () => {
      // Get the current path (excluding query strings or fragments)
      const currentPath = window.location.pathname.replace(/\/$/, "");
  
      // Select all navigation links
      const navLinks = document.querySelectorAll('.navbar-nav .nav-link');
  
      navLinks.forEach(link => {
          const linkPath = new URL(link.href).pathname.replace(/\/$/, "");
  
          // Add 'active' class if the link path matches the current path
          if (currentPath === linkPath) {
              link.classList.add('active');
          } else {
              link.classList.remove('active');
          }
      });
  });

  

//Veg revolution section
/*document.querySelectorAll('.read-more-btn').forEach((button) => {
  button.addEventListener('click', () => {
    const content = button.nextElementSibling;
    button.classList.toggle('active');
    
    if (content.style.display === 'block') {
      content.style.display = 'none';
    } else {
      content.style.display = 'block';
    }
  });
});
*/

/*function toggleContent(button) {
  const hiddenContent = button.closest('.card').querySelector('.hidden-content');
  hiddenContent.classList.toggle('d-none'); // Show/hide the content
  button.textContent = hiddenContent.classList.contains('d-none') ? 'Read More' : 'Read Less'; // Toggle button text
}*/

function toggleGrid(button) {
  const hiddenGrid = button.closest('.card').querySelector('.hidden-grid');
  // Toggle visibility
  hiddenGrid.classList.toggle('d-none');
  // Update button text
  button.textContent = hiddenGrid.classList.contains('d-none') ? 'Read More' : 'Read Less';
}
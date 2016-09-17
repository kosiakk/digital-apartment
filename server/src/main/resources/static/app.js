/**
 * Created by Alexander Kosenkov on 17.09.2016.
 */

function refr() {

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {

            var fetched = xhr.response;
            if (fetched) {

                var el = document.createElement( 'html' );
                el.innerHTML = fetched;

                var bodyNew = el.getElementsByTagName("body");
                var bodyOld = document.getElementsByTagName("body");
                morphdom(bodyOld[0], bodyNew[0]);
            }

            setTimeout(refr, 5000);
        }
    };

    xhr.open('GET', window.location.href, true);
    xhr.send();
}

setTimeout(refr, 5000);

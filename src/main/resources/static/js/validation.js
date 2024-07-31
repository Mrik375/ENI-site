document.addEventListener("DOMContentLoaded", function() {
    var formulaire = document.getElementById("form-creer"); // formulaire
    formulaire.addEventListener("submit", function(e) {
        let motDePasse = document.getElementById("motDePasse").value; // champ de mot de passe
        let confirmation = document.getElementById("confirmMDP").value; // champ de confirmation
        let messageErreur = document.getElementById("js-erreur"); //paragraphe pour le message d'erreur
        let titreH2 = document.getElementById("titreH2").textContent; //valeur du titre <h2> de la page
        let english = "New profile";
        let francais = "Nouveau profil";

		console.log("mot de passe : " + motDePasse + " confirmation : " + confirmation);

         // Efface le message d'erreur précédent
        messageErreur.textContent = "";

        if(motDePasse !== confirmation && titreH2 == francais) {
            e.preventDefault(); // Empêche la soumission du formulaire
            
             // Ajoute le message d'erreur
            messageErreur.textContent = "Les mots de passe ne correspondent pas.";
            messageErreur.style.color = "#ff6a6a";
        }
        
        if(motDePasse !== confirmation && titreH2 == english) {
            e.preventDefault(); // Empêche la soumission du formulaire
            
             // Ajoute le message d'erreur
            messageErreur.textContent = "The confirmation do not match the password";
            messageErreur.style.color = "#ff6a6a";
        }
    });
});


<%-- ######### Liste des imports ############ --%>
<%@ page import="Conf.ConfString" %>


<%-- ######### Liste des noms des noms des attributs ############ --%>
<%
        String lang = "Language";
        int _TIME_OUTdeb = 3;
        int _TIME_OUTfin = 10;
        int _TIME_OUTpas = 2;
        boolean defauttimeout = false;
        HttpSession client = request.getSession();

%>

<%-- ######### methode de conservation de la saisie ############ --%>
<%!

// M�thodes
    /**
     * M�thode qui redonne la valeur li�e � un objet html
     */
    private String getParam(HttpServletRequest request, String param) {

        if (request.getParameter(param) == null) {
            return "";
        } else {
            return request.getParameter(param);
        }
    }

    /**
     * M�thode qui permet de notifier � l'utilisateur l'id de la session
     */
    private String idSession(HttpServletRequest request) {
        return "<i id=\"couleurvert\">" + request.getSession().getId() + "</i><br/>";
    }

    /**
     * M�thode qui permet de sauvegarder ce qui a �t� s�lectionner pr�c�demment
     * sans revenir � la valeur par d�faut dans un menu d�roulant
     */
    private String SessionSelected(HttpServletRequest request, String name, String def) {
        String resultat = def;
        Object val = request.getSession().getAttribute(name);
        if (val != null) {
            resultat = val.toString();
        }
        return resultat;
    }

    /**
     * M�thode pour emp�cher de traiter un morceau du formulaire qui devrait
     * �tre rempli
     */
    private String ErreurScript(HttpServletRequest request, String name) {
        String resultat = "<br/>";
        Object val = request.getSession().getAttribute(name);
        if (val != null) {
            resultat = "<i id=\"couleured\">" + val.toString() + "</i><br/>";
        }
        return resultat;
    }

    /**
     * M�thode qui permet de sauvegarder ce qui a �t� �crit pr�c�demment
     * sans perdre en cas de rechargement de la page
     */
    private String SessionTextArea(HttpServletRequest request, String name) {
        String resultat =ConfString.CodeInitial2 ;
        Object val = request.getSession().getAttribute(name);
        if (val != null) {
            resultat = val.toString();
        }
        return resultat;
    }
    
    /**
     * M�thode qui permet de sauvegarder ce qui a �t� �crit pr�c�demment
     * sans perdre en cas de rechargement de la page
     */
    private String RedonneString(HttpServletRequest request, String name) {
        String resultat = "";
        Object val = request.getSession().getAttribute(name);
        if (val != null) {
            resultat = val.toString();
        }
        return resultat;
    }
    
    private int tailleTextArea(boolean condition){
    	int taille =4;
    	if (!condition){
    		taille =22;
    	}
    	return taille;
    }

    /**
     * M�thode qui cache les objets name tant qu'il n'y a pas possibilit� de les
     * utiliser � cause de la condition
     * cond=true signifie qu'on peut arcacher
     */
    private boolean isHidden(HttpServletRequest request, String cond) {
        String resultat = ConfString.hidden;
        Object val = request.getSession().getAttribute(cond);
        Boolean valeur = new Boolean(true);
        if (val != null) {
            valeur = new Boolean(val.toString());
        }
        return valeur;
    }
    
    /**
     * M�thode qui redonne l'url du site qui est ex�cut�
     */
    private String RedonneURL(HttpServletRequest request){
    	StringBuffer urlLong=request.getRequestURL();
    	// on enl�ve index.jsp de l'url ou on enl�ve la partie derri�re le dernier /
    	int index=urlLong.lastIndexOf("/");
    	String urlCourt = urlLong.substring(0,index+1);
    	
    	return urlCourt;
    }

%>
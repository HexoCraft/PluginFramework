package com.github.hexosse.pluginframework.pluginapi.message;

import java.util.Locale;

/**
 * This file is part HexocubeItems
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 * @date 11/02/2016
 */
public class MessageText
{
	public static String help_for_command;

	public static String commmand_command;
	public static String commmand_description;
	public static String commmand_click_copy_command;
	public static String commmand_no_permission;
	public static String commmand_not_enough_parameters;
	public static String commmand_error;
	public static String commmand_use_help;

	public static String argument_argument;
	public static String argument_description;
	public static String argument_mandatory;


	public static String help_page;

	static { init(); }

	public static  void init()
	{
        if(Locale.getDefault().getLanguage()=="fr")
            initFR_fr();
        else if(Locale.getDefault().getLanguage()=="en")
            initEN_en();
        else
            initEN_en();
	}

	public static void initEN_en()
	{
		help_for_command = "Help for command";

		commmand_command = "Command";
		commmand_description = "Description";
		commmand_click_copy_command = "Click to copy the command";
		commmand_no_permission = "You do not have permission to perform that command !";
		commmand_not_enough_parameters = "Not enough parameters for the command. You should do use";
		commmand_error = "There is an error in your command";
		commmand_use_help = "Use help for more details";

		argument_argument = "Argument";
		argument_description = "Description";
		argument_mandatory = "This argument is required";

		help_page = "Page";
	}

	public static void initFR_fr()
	{
		help_for_command = "Aide pour la commande";

		commmand_command = "Commande";
		commmand_description = "Description";
		commmand_click_copy_command = "Click pour copier la commande";
		commmand_no_permission = "Vous n'avez pas les droits suffisants pour executer cette commande !";
		commmand_not_enough_parameters = "Pas assez de paramètres pour la commande, Vous devez utiliser";
		commmand_error = "Il y a une erreur dans la commande";
		commmand_use_help = "Utilisez l'aide pour plus de détails";

		argument_argument = "Argument";
		argument_description = "Description";
		argument_mandatory = "Cette argument est obligatoire";

		help_page = "Page";
	}
}

// Place your Spring DSL code here
beans = {
    customDateTimePropertyRegistrar(it.solvingteam.gespim.bindingutil.CustomDateTimePropertyRegistrar)
    
	myPropertyEditorRegistrar(it.solvingteam.gespim.bindingutil.MyPropertyEditorRegistrar)
}

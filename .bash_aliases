alias workspace="cd ${GITPOD_REPO_ROOT}"
alias webui="gp preview $(gp url 8080)"

alias ex1="cd ${GITPOD_REPO_ROOT}/exercises/sendings-signals/practice"
alias ex1s="cd ${GITPOD_REPO_ROOT}/exercises/sendings-signals/solution"
alias ex1w="mvn exec:java -Dexec.mainClass='sendingsignals.SendingSignalsWorker'"
alias ex1st="mvn exec:java -Dexec.mainClass='sendingsignals.Starter'"
alias ex1sg="mvn exec:java -Dexec.mainClass='sendingsignals.SignalClient'"

alias ex2="cd ${GITPOD_REPO_ROOT}/exercises/querying-workflows/practice"
alias ex2s="cd ${GITPOD_REPO_ROOT}/exercises/querying-workflows/solution"
alias ex2w="mvn exec:java -Dexec.mainClass='queryingworkflows.QueryingWorkflowsWorker'"
alias ex2st="mvn exec:java -Dexec.mainClass='queryingworkflows.Starter'"
alias ex2sg="mvn exec:java -Dexec.mainClass='queryingworkflows.SignalClient'"
alias ex2q="mvn exec:java -Dexec.mainClass='queryingworkflows.QueryClient'"


alias ex3="cd ${GITPOD_REPO_ROOT}/exercises/async-activity-completion/practice"
alias ex3s="cd ${GITPOD_REPO_ROOT}/exercises/async-activity-completion/solution"
alias ex3w="mvn exec:java -Dexec.mainClass='asyncactivitycompletion.AsyncActivityCompletionWorker'"
alias ex3st="mvn exec:java -Dexec.mainClass='asyncactivitycompletion.Starter'"
alias ex3sg="mvn exec:java -Dexec.mainClass='asyncactivitycompletion.SignalClient'"
echo "Your workspace is located at: ${GITPOD_REPO_ROOT}"
echo "Type the command     workspace      to return to the workspace directory at any time."

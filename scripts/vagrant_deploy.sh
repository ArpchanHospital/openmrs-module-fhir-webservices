PATH_OF_CURRENT_SCRIPT="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
source $PATH_OF_CURRENT_SCRIPT/vagrant_functions.sh
PROJECT_BASE=$PATH_OF_CURRENT_SCRIPT/..

mvn clean install -DskipTests

run_in_vagrant -c "sudo rm -rf /opt/openmrs/modules/fhir-webservices-*omod"
run_in_vagrant -c "sudo cp -f /bahmni/openmrs-module-fhir-webservices/omod/target/fhir-webservices-*omod /opt/openmrs/modules"
run_in_vagrant -c "sudo service openmrs stop"
run_in_vagrant -c "sudo service openmrs debug"

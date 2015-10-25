
def builders = [ 'bind', 'freeipa', 'nginx'];

def gitUrl = 'https://github.com/jediq/sprint0/builders/';

for (def builder : builders) {

    job {
        name (builder + ' image builder')

        scm {
            git(gitUrl + builder)
        }

        steps {
            shell('docker build -t sprint0/' + builder + ' .')
        }
    }
}
import subprocess

def compile_java(java_file):
    subprocess.run(['javac', '-d', 'src', '-cp', 'src', java_file])

def execute_java(java_class, args):
    print("java UserMgmt ", args)
    subprocess.run(['java', '-cp', 'src', java_class, *args])

if __name__ == "__main__":
    compile_java('src/UserMgmt.java')
    compile_java('src/AddUser.java')
    compile_java('src/ChangePass.java')
    compile_java('src/DeleteUser.java')
    compile_java('src/ForceChange.java')
    compile_java('src/FileManager.java')
    compile_java('src/Login.java')    
    print("\n")

    execute_java('UserMgmt', ['add', 'zutokljunac'])

    print("\n")

    execute_java('UserMgmt', ['add', 'zutokljunac'])

    print("\n")

    execute_java('Login', ['login', 'zutokljunac'])

    print("\n")

    execute_java('UserMgmt', ['passwd', 'zutokljunac'])

    print("\n")

    execute_java('UserMgmt', ['forcepass', 'zutokljunac'])

    print("\n")

    execute_java('Login', ['login', 'zutokljunac'])

    print("\n")

    execute_java('UserMgmt', ['del', 'zutokljunac'])

    print("\n")
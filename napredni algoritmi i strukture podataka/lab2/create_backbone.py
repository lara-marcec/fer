class Node:
    def __init__(self, value: int) -> None:
        self.value = value
        self.left = None
        self.right = None

class BinarySearchTree:
    def __init__(self) -> None:
        self.root = None

    def insert(self, value: int) -> None:
        if self.root is None:
            self.root = Node(value)
        else:
            self._insert(value, self.root)

    def _insert(self, value: int, node: Node) -> None:
        if value < node.value:
            if node.left is None:
                node.left = Node(value)
            else:
                self._insert(value, node.left)
        else:
            if node.right is None:
                node.right = Node(value)
            else:
                self._insert(value, node.right)

    def create_right_skewed(self) -> Node:
        """Konvertira BST u desnu kralježnicu."""
        self.head = None
        self.prev = None
        self._create_right_skewed(self.root)
        return self.head

    def _create_right_skewed(self, node: Node) -> None:
        """Pomoćna funkcija za stvaranje desne kralježnice."""
        if node is None:
            return
        
        # Prvo obradite lijevo podstablo
        self._create_right_skewed(node.left)

        # Zabilježite trenutni čvor
        if self.head is None:
            self.head = node  # Ovo postaje glava kralježnice
        else:
            self.prev.right = node  # Poveži prethodni čvor s trenutnim čvorom

        node.left = None  # Postavi lijevo na None
        self.prev = node  # Ažurirajte prethodni čvor

        # Obradite desno podstablo
        self._create_right_skewed(node.right)

    def create_left_skewed(self) -> Node:
        """Konvertira BST u lijevu kralježnicu."""
        self.head = None
        self.prev = None
        self._create_left_skewed(self.root)
        return self.head

    def _create_left_skewed(self, node: Node) -> None:
        """Pomoćna funkcija za stvaranje lijeve kralježnice."""
        if node is None:
            return
        
        # Prvo obradite desno podstablo
        self._create_left_skewed(node.right)

        # Zabilježite trenutni čvor
        if self.head is None:
            self.head = node  # Ovo postaje glava kralježnice
        else:
            self.prev.left = node  # Poveži prethodni čvor s trenutnim čvorom

        node.right = None  # Postavi desno na None
        self.prev = node  # Ažurirajte prethodni čvor

        # Obradite lijevo podstablo
        self._create_left_skewed(node.left)

    def print_tree(self, node: Node) -> None:
        """Ispisuje stablo (koristi se za vizualizaciju)."""
        if node is not None:
            self.print_tree(node.left)
            print(node.value, end=' ')
            self.print_tree(node.right)

# Primjer korištenja
bst = BinarySearchTree()
bst.insert(10)
bst.insert(5)
bst.insert(15)
bst.insert(3)
bst.insert(7)
bst.insert(12)
bst.insert(18)

print("Stablo prije konverzije:")
bst.print_tree(bst.root)

# Stvaranje desne kralježnice
right_skewed_head = bst.create_right_skewed()
print("\n\nDesna kralježnica:")
current = right_skewed_head
while current:
    print(current.value, end=' ')
    current = current.right

# Stvaranje lijeve kralježnice
bst = BinarySearchTree()  # Resetiranje stabla
bst.insert(10)
bst.insert(5)
bst.insert(15)
bst.insert(3)
bst.insert(7)
bst.insert(12)
bst.insert(18)

left_skewed_head = bst.create_left_skewed()
print("\n\nLijeva kralježnica:")
current = left_skewed_head
while current:
    print(current.value, end=' ')
    current = current.left

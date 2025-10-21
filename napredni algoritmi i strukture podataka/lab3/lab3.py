from __future__ import annotations # ne stavljati u Edgar
from math import floor, ceil
from typing import Tuple


class BTNode:

    def __init__(self, value: int = None, left: list = None, deg: int = 5, parent: BTNode = None) -> None:
        if value is not None:
            self.left = [None, value, None]
        else:
            self.left = left
            for item in self.left:
                if type(item) is BTNode:
                    item.parent = self
        self.deg = deg
        self.parent = parent

    def search_value(self, value: int) -> Tuple[BTNode, bool, int]:

        for index in range(len(self.left)):
            item = self.left[index]
            if type(item) is int:
                left_child = self.left[index-1]
                if value == item:
                    return (self, True, index)
                if value < item:
                    if left_child is not None:
                        return left_child.search_value(value)
                    else:
                        return (self, False, index)
        right_child = self.left[len(self.left) - 1]
        if right_child is not None:
            return right_child.search_value(value)
        else:
            return (self, False, len(self.left))

    def keys(self) -> int:

        return int((len(self.left) - 1) / 2)

    def min_keys(self) -> int:

        return ceil(self.deg / 2) - 1

    def insert_value(self, value: int) -> None:
        (node, found, index) = self.search_value(value)
        if not found:
            if index < len(node.left):
                node.left[index:index] = [value, None]
            else:
                node.left += [value, None]
            return node._split()
        return None

    def _split(self) -> BTNode:
        """
        Split the overflowing node.
        Returns:
            NTNode: Node after spliting.
        """
        if self.keys() > (self.deg-1): #ako je velicina cvora nakon dodavanja vrijednosti veca ili jednaka stupnju stabla
            i = floor(self.keys() / 2) * 2 + 1

            node_left = BTNode(left=self.left[:i], deg=self.deg, parent=self.parent)
            node_value = self.left[i]
            node_right = BTNode(left=self.left[i+1:], deg=self.deg, parent=self.parent)

            if self.parent is not None: #slucaj 2b, list ima roditelja
                index = self.parent.left.index(self)
                self.parent.left[index:index+1] = [node_left, node_value, node_right]
                return self.parent._split()
            
            else: #ako nema roditelja, znaci da imamo novi korijen, slucaj 2a
                new_root = BTNode(left= [node_left, node_value, node_right], deg=self.deg)
                node_left.parent = new_root
                node_right.parent = new_root
                return new_root

        return None

    def __str__(self) -> str:

        res = "|"
        for item in self.left:
            if type(item) is int:
                res += "{}".format(item)
            if type(item) is BTNode:
                res += "[K]"
            if item is None:
                res += "[N]"
        res += "|"
        return res


class BTreeList:

    def __init__(self, value: int, deg: int = 5) -> None:
        self.root = BTNode(value=value, deg=deg)
        self.deg = deg

    def search_value(self, value: int) -> bool:

        (_, found, _) = self.root.search_value(value)
        return found

    def insert_values(self, values: list) -> None:
        """
        Method for inserting values in the tree.
        Args:
            values (list): A list of values to be inserted.
        """

        for v in values:
            new_root = self.root.insert_value(v)
            
            if new_root is not None:
                self.root = new_root
        #TODO: Insert new values and update root
       

    def __str__(self) -> str:
        res, q = "", [self.root, "\n"]
        while q != ["\n"]:
            n = q[0]
            q = q[1:]
            if type(n) is str:
                res += n
                q.append("\n")
            else:
                res += str(n)
                for item in n.left:
                    if type(item) is BTNode:
                        q.append(item)
        return res
    

values = [3, 31, 20, 13, 16, 28, 44, 11]
btree = BTreeList(values[0], deg=3)
btree.insert_values(values[1:])
print(btree)
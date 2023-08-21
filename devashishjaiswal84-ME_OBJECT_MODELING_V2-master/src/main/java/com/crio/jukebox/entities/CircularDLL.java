package com.crio.jukebox.entities;

public class CircularDLL {

    public class Node {
        // Song song;
        // Node prev;
        // Node next;

        // Node(Song song) {
        // this.song = song;
        // }
        private Song song;
        private Node next;
        private Node prev;

        public Node(Song song) {
            this.song = song;
            this.next = null;
            this.prev = null;
        }

        public Song getSong() {
            return song;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrevious() {
            return prev;
        }

        public void setPrevious(Node prev) {
            this.prev = prev;
        }
    }

    private Node head;

    public Node getHead() {
        return head;
    }

    public void addSong(Song song) {
        Node newNode = new Node(song);
        if (head == null) {
            head = newNode;
            head.next = head;
            head.prev = head;
        } else {
            Node last = head.prev;
            last.next = newNode;
            newNode.prev = last;
            newNode.next = head;
            head.prev = newNode;
        }
    }

    public void removeSong(Song song) {
        if (head == null) {
            return; // List is empty
        }

        Node current = head;

        while (current.song != song) {
            if (current.next == head) {
                return; // Song not found in the list
            }
            current = current.next;
        }

        if (current == head) {
            if (head.next == head) {
                head = null; // Only one node
            } else {
                Node last = head.prev;
                head = head.next;
                last.next = head;
                head.prev = last;
            }
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
    }

    public Song playNextSong() {
        if (head == null) {
            return null; // List is empty
        }
        head = head.next;
        return head.song;
    }

    public Song playPreviousSong() {
        if (head == null) {
            return null; // List is empty
        }
        head = head.prev;
        return head.song;
    }

    public Node findNode(int songId) {
        Node current = head;
        while(current.next != head){
            if(current.getSong().getId() == songId){
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void setCurrentNode(Node node) {
        head = node;
    }


}

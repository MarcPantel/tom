<!-- For each book that has at least one author, list the title and first two authors, and an empty "et-al" element if the book has additional authors. 

Solution in XQuery: -->

<bib>
  {
    for $b in doc("http://bstore1.example.com/bib.xml")//book
    where count($b/author) > 0
    return
        <book>
            { $b/title }
            {
                for $a in $b/author[position()<=2]  
                return $a
            }
            {
                if (count($b/author) > 2)
                 then <et-al/>
                 else ()
            }
        </book>
  }
</bib>

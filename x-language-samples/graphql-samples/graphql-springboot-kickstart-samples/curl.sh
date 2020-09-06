#!/bin/bash

curl -X POST 'http://localhost:9093/graphql' -H 'Content-Type: application/graphql' -d '{hello}'
echo ''
echo ''
curl -X POST 'http://localhost:9093/graphql' -H 'Content-Type: application/graphql' -d 'mutation{
    hello
}'
echo ''
echo ''
curl -X POST 'http://localhost:9093/graphql' -H 'Content-Type: application/graphql' -d '{
    bookById(id : 1){
        id
        name
        createdAt
        author{
            firstName
            lastName
        }
    }
}'
echo ''
echo ''
curl -X POST 'http://localhost:9093/graphql' -H 'Content-Type: application/graphql' -d '{
    booksByInput(book: {id : "", name: "Harry"}){
        id
        name
        pageCount
    }
}'
echo ''
echo ''
curl -X POST 'http://localhost:9093/graphql' -H 'Content-Type: application/graphql' -d '{
    books(offset: 0, first: 5){
        edges {
            cursor
            node{
                id
                name
                pageCount
            }
        }
        pageInfo{
            hasPreviousPage
            hasNextPage
        }
    }
}'
echo ''
echo ''

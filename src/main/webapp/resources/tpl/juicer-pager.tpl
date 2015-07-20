<div class="page">
    {@if (pageSize > totalCount) || totalCount}
        <span class="pageTotal" data-i18n="common.pager.totalPage"></span>
        {@if page > 1}
        <a href="javascript:void(0)" onclick="gotoPage(1, '!{data}')" class="pageNum pageShortCut"><i>&lt;&lt;</i> <span data-i18n="common.pager.home"></span></a>
        <a href="javascript:void(0)" onclick="gotoPage(!{page - 1}, '!{data}')" class="pageNum pageShortCut"><i class="gray">&lt;</i><i>&lt;</i> <span data-i18n="common.pager.previous"></span></a>
        {@/if}
        {@if totalPage > 10}
            {@each index in range(page-4, page+5)}
                {@if index >= 1 && index<=totalPage}
                <a href="javascript:void(0)" {@if page==index} class="pageNum currentPage" {@else} onclick="gotoPage(!{index}, '!{data}')" class="pageNum" {@/if}>!{index}</a>
                {@/if}
            {@/each}
        {@else}
            {@each index in range(1, totalPage+1)}
                <a href="javascript:void(0)" {@if page==index} class="pageNum currentPage" {@else} onclick="gotoPage(!{index}, '!{data}')" class="pageNum" {@/if}>!{index}</a>
            {@/each}
        {@/if}
        {@if parseInt(page, 10) < totalPage}
            <a href="javascript:void(0)" onclick="gotoPage(!{parseInt(page, 10) + 1}, '!{data}')" class="pageNum pageShortCut"><span data-i18n="common.pager.next"></span> <i>&gt;</i><i class="gray">&gt;</i></a>
            <a href="javascript:void(0)" onclick="gotoPage(!{totalPage}, '!{data}')" class="pageNum pageShortCut"><span data-i18n="common.pager.end"></span> <i>&gt;&gt;</i></a>
        {@/if}
    {@else}
        {# No Paging }
    {@/if}
</div>